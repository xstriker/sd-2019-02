defmodule Server.Repo.Migrations.CreateCurriculuns do
  use Ecto.Migration

  def change do
    create table(:curriculuns) do
      add :nome, :string
      add :contato, :string
      add :area, :string
      add :tempo, :integer
      add :salario, :integer

      timestamps()
    end

  end
end
